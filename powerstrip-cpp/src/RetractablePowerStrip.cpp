#include <iostream>
#include "RetractablePowerStrip.h"

namespace powerstrip {
  
  RetractablePowerStrip::RetractablePowerStrip(int outlets)
    : PowerStrip(outlets), isRetracted(false)
  {
    std::cout << "made RetractablePowerStrip@" << ((void*) this) << std::endl;
  }

  void RetractablePowerStrip::extend() {
    isRetracted = false;
  }
  void RetractablePowerStrip::retract() {
    isRetracted = true;
  }
  bool RetractablePowerStrip::isExtended() const { return ! isRetracted; }

  OutletState RetractablePowerStrip::getOutletState(int outlet) const {
    return isExtended() ? PowerStrip::getOutletState(outlet) : OutletState::UNPOWERED;
  }
  RetractablePowerStrip::~RetractablePowerStrip() {
  }
} // powerstrip
