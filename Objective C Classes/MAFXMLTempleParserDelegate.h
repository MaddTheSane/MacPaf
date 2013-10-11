//
//  MAFXMLTempleParserDelegate.h
//  MAF
//
//  Created by C.W. Betts on 10/10/13.
//
//

#import <Foundation/Foundation.h>

@interface MAFXMLTempleParserDelegate : NSObject <NSXMLParserDelegate>

@property (unsafe_unretained, readonly) NSDictionary *templeDictionary;

@end
