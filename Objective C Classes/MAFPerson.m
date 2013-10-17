//
//  Person.m
//  MAF
//
//  Created by C.W. Betts on 10/17/13.
//
//

#import "MAFPerson.h"

@implementation MAFPerson

@synthesize notes;

- (void)setGenderWithString:(NSString*)gen
{
	if (gen == nil && ![gen isEqualToString:@""]) {
		self.gender = MAFGenderUnknown;
	} else {
		if ([@"m" compare:gen options:(NSCaseInsensitiveSearch | NSDiacriticInsensitiveSearch | NSWidthInsensitiveSearch) range:[gen rangeOfComposedCharacterSequencesForRange:NSMakeRange(0, 1)] ]== NSOrderedSame) {
			self.gender = MAFGenderMale;
		} else {
			self.gender = MAFGenderFemale;
		}
	}
}

- (void)addNoteObject:(NSMutableString*)noteobj
{
	[self.notes addObject:noteobj];
}

- (void)removeNoteObject:(NSMutableString*)value
{
	[self.notes removeObject:value];
}

@dynamic birthDate;
@dynamic burialDate;
@dynamic deathDate;
@dynamic firstName;
@dynamic gender;
@dynamic lastName;
@dynamic middleNames;
@dynamic children;
@dynamic relationship;
@dynamic relationship1;
@dynamic spouses;

@dynamic birthPlace;
@dynamic burialPlace;
@dynamic deathPlace;
@dynamic afn;

@end
