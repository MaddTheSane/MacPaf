//
//  Person.h
//  MAF
//
//  Created by C.W. Betts on 10/17/13.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

typedef NS_ENUM(int16_t, MAFGenders)
{
	MAFGenderUnknown = 0,
	MAFGenderMale,
	MAFGenderFemale
};

@class MAFFamily;

@interface MAFPerson : NSManagedObject

@property (strong) NSMutableArray *notes;
@property (nonatomic, retain) NSDate * birthDate;
@property (nonatomic, retain) NSDate * burialDate;
@property (nonatomic, retain) NSDate * deathDate;
@property (nonatomic, retain) NSString * firstName;
@property (nonatomic) int16_t gender;
@property (nonatomic, retain) NSString * lastName;
@property (nonatomic, retain) NSString * middleNames;
@property (nonatomic, retain) NSSet *children;
@property (nonatomic, retain) MAFFamily *relationship;
@property (nonatomic, retain) MAFFamily *relationship1;
@property (nonatomic, retain) NSSet *spouses;
@property (nonatomic) int64_t birthPlace;
@property (nonatomic) int64_t burialPlace;
@property (nonatomic) int64_t deathPlace;
@property (nonatomic, retain) NSString *afn;
- (void)setGenderWithString:(NSString*)gen;
- (void)addNoteObject:(NSMutableString*)noteobj;
- (void)removeNoteObject:(NSMutableString*)value;

@end

@interface MAFPerson (CoreDataGeneratedAccessors)

- (void)addChildrenObject:(MAFFamily *)value;
- (void)removeChildrenObject:(MAFFamily *)value;
- (void)addChildren:(NSSet *)values;
- (void)removeChildren:(NSSet *)values;

- (void)addSpousesObject:(MAFPerson *)value;
- (void)removeSpousesObject:(MAFPerson *)value;
- (void)addSpouses:(NSSet *)values;
- (void)removeSpouses:(NSSet *)values;

@end
