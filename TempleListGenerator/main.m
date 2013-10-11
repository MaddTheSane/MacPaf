//
//  main.m
//  TempleListGenerator
//
//  Created by C.W. Betts on 10/10/13.
//
//

#import <Foundation/Foundation.h>
#import "MAFXMLTempleParserDelegate.h"

int main(int argc, const char * argv[])
{
	@autoreleasepool {
		if (argc != 3) {
			NSLog(@"Usage: ./TempleListGenerator \"output directory\" \"path to xml\"");
			abort();
			return EXIT_FAILURE;
		}
		NSFileManager *fm = [NSFileManager defaultManager];
		NSURL *destURL = [NSURL fileURLWithPath:[fm stringWithFileSystemRepresentation:argv[1] length:strlen(argv[1])] isDirectory:YES];
		NSURL *xmlURL = [NSURL fileURLWithPath:[fm stringWithFileSystemRepresentation:argv[2] length:strlen(argv[2])] isDirectory:NO];
		
		MAFXMLTempleParserDelegate *parseDelegate = [[MAFXMLTempleParserDelegate alloc] init];
		
		NSXMLParser *parser = [[NSXMLParser alloc] initWithContentsOfURL:xmlURL];
		parser.delegate = parseDelegate;
		[parser parse];
		
		[[parseDelegate templeDictionary] writeToURL:[destURL URLByAppendingPathComponent:@"Temple List.plist"] atomically:NO];
	}
	return EXIT_SUCCESS;
}

