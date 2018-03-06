#pragma once

#include "PowerStrip.h"

namespace powerstrip {
  class RetractablePowerStrip : public PowerStrip {
  private: bool isRetracted;
  public: RetractablePowerStrip(int outlets);
  public: virtual void extend();
  public: virtual void retract();
  public: virtual bool isExtended() const;
  public: virtual OutletState getOutletState(int outlet) const override;    
  public: virtual ~RetractablePowerStrip();    
  };
} // powerstrip
