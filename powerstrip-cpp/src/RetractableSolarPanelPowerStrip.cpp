#include <iostream>
#include "RetractableSolarPanelPowerStrip.h"

namespace powerstrip {
    RetractableSolarPanelPowerStrip::RetractableSolarPanelPowerStrip(int outlets)
       : RetractableSolarPanel(), RetractablePowerStrip(outlets)
    {
      std::cout << "made RetractableSolarPanelPowerStrip@" << ((void*) this) << std::endl;      
    }
}


//
// Created by user on 3/22/18.
//

