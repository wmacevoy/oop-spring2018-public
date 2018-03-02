#pragma once

#include <vector>
#include <memory>
#include "OutletState.h"
#include "Mode.h"

namespace powerstrip {
  class PowerStrip {
  public: typedef std::shared_ptr < PowerStrip > Ptr;
  protected: int outlets;
  protected: std::vector < OutletState > outletStates;
  protected: Mode mode;

  public: PowerStrip(int _outlets);
  public: virtual int getOutlets() const;
  public: virtual void setMode(Mode _mode);
  public: virtual Mode getMode() const;
  public: virtual void outletOk(int outlet) const;
  public: virtual void setOutletState(int outlet, OutletState state);
  public: virtual OutletState getOutletState(int outlet) const;
  public: virtual void setModeOff();
  public: virtual void setModeOn();
  public: virtual void setModeRemote();
  public: virtual ~PowerStrip();
  };


  
} // powerstrip
