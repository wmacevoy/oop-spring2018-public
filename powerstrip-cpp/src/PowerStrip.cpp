#include "PowerStrip.h"
#include <stdexcept>
#include <sstream>

namespace powerstrip {
  PowerStrip::PowerStrip(int _outlets)
    : outlets(_outlets > 0 ? _outlets : 0), outletStates(outlets, OutletState::UNPOWERED), mode(Mode::REMOTE)
  {
    if (outlets <= 0) {
      std::ostringstream oss;
      oss << "outlets (" << outlets << ") must be positive.";
      throw std::out_of_range(oss.str());
    }
  }

  int PowerStrip::getOutlets() const {
    return outlets;
  }

  void PowerStrip::setMode(Mode _mode) {
    mode = _mode;
  }

  Mode PowerStrip::getMode() const {
    return mode;
  }

  void PowerStrip::outletOk(int outlet) const {
    if (outlet < 0 || outlet >= outlets) {
      std::ostringstream oss;
      oss << "outlet (" << outlet << ") must be between 0 and " << (outlets-1) << ".";
      throw std::out_of_range(oss.str());
    }
  }

  void PowerStrip::setOutletState(int outlet, OutletState state) {
    outletOk(outlet);
    outletStates[outlet]=state;
  }

  OutletState PowerStrip::getOutletState(int outlet) const {
    outletOk(outlet);
    if (mode == Mode::ON) return OutletState::POWERED;
    else if (mode == Mode::OFF) return OutletState::UNPOWERED;
    else return outletStates[outlet];
  }

  void PowerStrip::setModeOff() { setMode(Mode::OFF); }
  void PowerStrip::setModeOn() { setMode(Mode::ON); }
  void PowerStrip::setModeRemote() { setMode(Mode::REMOTE); }
  
}
