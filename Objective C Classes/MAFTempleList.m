//
//  MAFTempleList.m
//  MAF
//
//  Created by C.W. Betts on 10/9/13.
//
//

#import "MAFTempleList.h"

NSString * const kMAFTempleNumber = @"MAF Temple Number";

static MAFTempleList *permTempleList;

@interface MAFTempleList ()
@property (strong) NSDictionary *templeList;
@end

@implementation MAFTempleList

- (id)init
{
	if (self = [super init]) {
		
	}
	return self;
}

+ (NSDictionary*)templeWithCode:(NSString*)theCode
{
	static dispatch_once_t onceToken;
	dispatch_once(&onceToken, ^{
		permTempleList = [[MAFTempleList alloc] init];
	});
}
	
@end
