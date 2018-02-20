#pragma once

#include <vector>
#include "OutletState.h"
#include "Mode.h"

namespace powerstrip {
  class PowerStrip {

  protected: int outlets;
  protected: std::vector < OutletState > outletStates;
  protected: Mode mode;

  public: PowerStrip(int _outlets);
  public: int getOutlets() const;
  public: void setMode(Mode _mode);
  public: Mode getMode() const;
  public: void outletOk(int outlet) const;
  public: void setOutletState(int outlet, OutletState state);
  public: OutletState getOutletState(int outlet) const;
  public: void setModeOff();
  public: void setModeOn();
  public: void setModeRemote();
  };
} // powerstrip
