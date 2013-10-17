//
//  MAFFamily.h
//  MAF
//
//  Created by C.W. Betts on 10/17/13.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class MAFPerson;

@interface MAFFamily : NSManagedObject

@property (nonatomic, retain) NSDate *divorceDate;
@property (nonatomic, retain) NSDate *marriageDate;
@property (nonatomic, retain) NSDate *sealingDate;
@property (nonatomic) int32_t sealingTemple;
@property (nonatomic) int64_t marriageLocation;
@property (nonatomic, retain) NSSet *children;
@property (nonatomic, retain) MAFPerson *father;
@property (nonatomic, retain) MAFPerson *mother;
@end

@interface MAFFamily (CoreDataGeneratedAccessors)

- (void)addChildrenObject:(MAFPerson *)value;
- (void)removeChildrenObject:(MAFPerson *)value;
- (void)addChildren:(NSSet *)values;
- (void)removeChildren:(NSSet *)values;

@end
