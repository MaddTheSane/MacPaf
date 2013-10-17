//
//  MAFLocation.h
//  MAF
//
//  Created by C.W. Betts on 10/17/13.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface MAFLocation : NSManagedObject

@property (nonatomic) double lattidude;
@property (nonatomic, retain) NSString * locationName;
@property (nonatomic) double longitude;
@property (nonatomic) int64_t locationNumber;

@end
