//
//  UKProgressPanel-private.h
//  MAF
//
//  Created by C.W. Betts on 10/20/13.
//
//

#ifndef MAF_UKProgressPanel_private_h
#define MAF_UKProgressPanel_private_h

#import <Foundation/NSLock.h>

extern NSLock* gUKProgressPanelThreadLock; // Mutex lock used to allow calling this from several threads.


#endif
