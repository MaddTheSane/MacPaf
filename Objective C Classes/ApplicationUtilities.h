//
//  ApplicationUtilities.h
//  MAF
//
//  Created by C.W. Betts on 10/24/13.
//
//

#import <Foundation/Foundation.h>

@interface ApplicationUtilities : NSObject <NSApplicationDelegate>
@property BOOL didFinish;
@property (weak) IBOutlet NSWindow *splashScreen;
@end
