//
//  MAFXMLTempleParserDelegate.m
//  MAF
//
//  Created by C.W. Betts on 10/10/13.
//
//

#import "MAFXMLTempleParserDelegate.h"

@interface MAFXMLTempleParserDelegate ()
@property (strong) NSMutableDictionary *internalTempleList;
@end

@implementation MAFXMLTempleParserDelegate

- (void) parser:(NSXMLParser *) parser didStartElement:(NSString *) elementName namespaceURI:(NSString *) namespaceURI qualifiedName:(NSString *) qName attributes:(NSDictionary *) attributes
{

}

- (void) parser:(NSXMLParser *) parser didEndElement:(NSString *) elementName namespaceURI:(NSString *) namespaceURI qualifiedName:(NSString *) qName
{

}

- (void) parser:(NSXMLParser *) parser foundCharacters:(NSString *) string
{

}

- (id)init
{
	if (self = [super init]) {
		self.internalTempleList = [NSMutableDictionary new];
	}
	return self;
}

- (NSDictionary*)templeDictionary
{
	return [NSDictionary dictionaryWithDictionary:self.internalTempleList];
}
	
@end
