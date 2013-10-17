//
//  MAFTempleList.h
//  MAF
//
//  Created by C.W. Betts on 10/9/13.
//
//

#import <Cocoa/Cocoa.h>

extern NSString * const kMAFTempleNumber;

@interface MAFTempleList : NSObject <NSComboBoxDataSource>
+ (NSDictionary*)templeWithCode:(NSString*)theCode;
@end
