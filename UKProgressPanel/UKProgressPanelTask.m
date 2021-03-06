/* =============================================================================
	POJECT:		UKProgressPanel
	FILE:		UKProgressPanelTask.m
	PURPOSE:	A single "task" (i.e. progress bar and status text field) for
				our MT-Newswatcher/Finder-style progress window for keeping the
				user current on concurrently running tasks.
	AUTHORS:	M. Uli Kusterer (UK), (c) 2003, all rights reserved.
	
	REQUIRES:	UKProgressPanelTask.h
				UKProgressPanelTask.nib
				UKProgressPanel.h
				UKProgressPanel.m
				(UKProgressPanel.nib)
				(UKProgressPanel.strings)
	
	NOTE:		UKProgressPanel and UKProgressPanelTask are thread-safe.
	
	DIRECTIONS:
			Using the progress panel is very simple: Use newProgressPanelTask
			and consorts to create a new task when you begin your lengthy
			operation, and release it when you have finished.
			
			The task will automatically take care of creating and showing the
			progress panel if needed, and will add itself to the shared
			progress panel instance.
			
			A task supports most of the methods of NSProgressIndicator to
			allow you to control the progress bar without any deep-reaching
			code changes. Use setTitle: to provide a general title indicating
			what action this is (e.g. "Emptying the trash"). The title will be
			displayed in bold above the progress bar. Use setStatus: to provide
			information about the step currently being executed, expected time
			to finish etc. - This will be displayed below the progress bar.
   ========================================================================== */

/* -----------------------------------------------------------------------------
	Headers:
   -------------------------------------------------------------------------- */

#import "UKProgressPanelTask.h"
#import "UKProgressPanel.h"
#import "UKProgressPanel-private.h"


/* -----------------------------------------------------------------------------
	Globals:
   -------------------------------------------------------------------------- */

@interface UKProgressPanelTask ()
@property (readwrite, getter = stopped) BOOL stopped; // Has this task been stopped by the user?
@end

@implementation UKProgressPanelTask
@synthesize stopDelegate;
@synthesize stopAction;
@synthesize stopped;
@synthesize progressTaskView;

/* -----------------------------------------------------------------------------
	newProgressPanelTask:
		Convenience constructor for creating a task. This automatically adds
		the task to the shared progress panel, creating one if necessary.
		
		Caller is responsible for releasing the result of this.
   -------------------------------------------------------------------------- */

+(id)newProgressPanelTask
{
	UKProgressPanelTask* el = [[self alloc] init];
	
	[[UKProgressPanel sharedProgressPanel] addProgressPanelTask: el];
	
	return el;
}


/* -----------------------------------------------------------------------------
	Constructor:
		Do not call this unless you really know what you're doing.
   -------------------------------------------------------------------------- */

-(id)init
{
	if(self = [super init])
	{
		[NSBundle loadNibNamed: @"UKProgressPanelTask" owner: self];
		self.stopAction = @selector(stop:);
		//[progressStopButton setEnabled: NO];
		self.stopped = NO;
	}
	
	return self;
}


/* -----------------------------------------------------------------------------
	Destructor:
		Makes sure we no longer belong to our progress window.
   -------------------------------------------------------------------------- */

#if 0
-(void)dealloc
{
	//TODO: call this at the right place
	[[UKProgressPanel sharedProgressPanel] removeProgressPanelTask: self];
}
#endif

/* -----------------------------------------------------------------------------
	awakeFromNib
		Makes sure the "stop" button is enabled if we have a stop delegate.
   -------------------------------------------------------------------------- */

-(void)awakeFromNib
{
	//[progressStopButton setEnabled: (stopDelegate != nil)];
}


/* -----------------------------------------------------------------------------
	Controlling the progress bar:
		These methods simply forward the messages to our progress bar.
   -------------------------------------------------------------------------- */

-(double)minValue
{
	return [progressBar minValue];
}

-(double)maxValue
{
	return [progressBar maxValue];
}

-(void)setMinValue:(double)newMinimum
{
	[gUKProgressPanelThreadLock lock];
	[progressBar setMinValue: newMinimum];
	[gUKProgressPanelThreadLock unlock];
}
-(void)setMaxValue:(double)newMaximum
{
	[gUKProgressPanelThreadLock lock];
	[progressBar setMaxValue: newMaximum];
	[gUKProgressPanelThreadLock unlock];
}

-(double)doubleValue
{
	return [progressBar doubleValue];
}

-(void)setDoubleValue:(double)doubleValue
{
	[gUKProgressPanelThreadLock lock];
	[progressBar setDoubleValue: doubleValue];
	[progressBar setNeedsDisplay:YES];
	[gUKProgressPanelThreadLock unlock];
}

-(void)incrementBy:(double)delta
{
	[gUKProgressPanelThreadLock lock];
	[progressBar incrementBy: delta];
	[progressBar setNeedsDisplay:YES];
	[gUKProgressPanelThreadLock unlock];
}

-(BOOL)isIndeterminate
{
	return [progressBar isIndeterminate];
}

-(void)setIndeterminate:(BOOL)flag
{
	[gUKProgressPanelThreadLock lock];
	[progressBar setIndeterminate: flag];
	[gUKProgressPanelThreadLock unlock];
}

-(void)animate:(id)sender
{
	[gUKProgressPanelThreadLock lock];
	[progressBar startAnimation:sender];
	[progressBar setNeedsDisplay:YES];
	[gUKProgressPanelThreadLock unlock];
}


/* -----------------------------------------------------------------------------
	title/status:
   -------------------------------------------------------------------------- */

- (NSString*)title
{
	NSString *title;
	[gUKProgressPanelThreadLock lock];
	title = progressTitleField.stringValue;
	[progressTitleField setNeedsDisplay:YES];
	[gUKProgressPanelThreadLock unlock];
	return title;
}

-(void)setTitle:(NSString*)title
{
	[gUKProgressPanelThreadLock lock];
	progressTitleField.stringValue = title;
	[progressTitleField setNeedsDisplay:YES];
	[gUKProgressPanelThreadLock unlock];
}

- (NSString *)status
{
	NSString *status;
	[gUKProgressPanelThreadLock lock];
	status = progressStatusField.stringValue;
	[progressStatusField setNeedsDisplay:YES];
	[gUKProgressPanelThreadLock unlock];
	return status;
}

-(void)setStatus:(NSString*)status
{
	[gUKProgressPanelThreadLock lock];
	progressStatusField.stringValue = status;
	[progressStatusField setNeedsDisplay:YES];
	[gUKProgressPanelThreadLock unlock];
}

/* -----------------------------------------------------------------------------
	setStopDelegate:
		Use this to specify an object to be sent the stopAction (defaults to
		@selector(stop:)) when the user clicks the "Stop" button in this
		panel. If you don't specify a stop delegate, the stop button will be
		disabled. (I'm not hiding it since that's too much work in Cocoa and
		I want to encourage writing abortable operations).
		
		This defaults to nil, meaning no notification will be sent.
   -------------------------------------------------------------------------- */

#if 0
-(void)setStopDelegate: (id)target
{
	stopDelegate = target;
	//[progressStopButton setEnabled: (stopDelegate != nil)];
}


/* -----------------------------------------------------------------------------
	stopDelegate:
		Returns the delegate that will be notified of clicks in the "Stop"
		button.
   -------------------------------------------------------------------------- */

-(id)stopDelegate
{
	return stopDelegate;
}
#endif

/* -----------------------------------------------------------------------------
	setStopAction:
		Use this to specify the message (defaults to @selector(stop:)) to be
		sent to the stopDelegate when the user clicks the "Stop" button in this
		panel. If you don't specify a stop delegate, the stop button will be
		disabled. (I'm not hiding it since that's too much work in Cocoa and
		I want to encourage writing abortable operations).
   -------------------------------------------------------------------------- */

#if 0
-(void)setStopAction: (SEL)action
{
	stopAction = action;
}


/* -----------------------------------------------------------------------------
	stopAction:
		The message (defaults to @selector(stop:)) to be sent to the
		stopDelegate when the user clicks the "Stop" button in this panel.
   -------------------------------------------------------------------------- */

-(SEL)stopAction
{
	return stopAction;
}


/* -----------------------------------------------------------------------------
	stopped:
		Accessor for the flag that is set by our "stop:" action method when the
		user clicks the "Stop" button.
   -------------------------------------------------------------------------- */

-(BOOL)stopped
{
	return stopped;
}
#endif

/* -----------------------------------------------------------------------------
	stop:
		This method is called by the "Stop" button whenever it's been clicked.
		
		It does two things: It sets this object's "stopped" flag that you can
		check, and it sends the stopAction to the stopDelegate.
   -------------------------------------------------------------------------- */

-(IBAction)stop:(id)sender
{
	[gUKProgressPanelThreadLock lock];
	[progressStopButton setEnabled: NO];
	self.stopped = YES;
	SEL		vStopAction = self.stopAction;
	id		vStopDelegate = self.stopDelegate;
	[gUKProgressPanelThreadLock unlock];
	
	[vStopDelegate performSelector: vStopAction withObject: sender];
}


/* -----------------------------------------------------------------------------
	progressTaskView:
		Returns the view containing the title/status fields and progress bar
		for this task.
   -------------------------------------------------------------------------- */

#if 0
-(NSView*)	progressTaskView
{
	return progressTaskView;
}
#endif

@end
