#include <iostream>
#include "Retractable.h"

namespace powerstrip {
  Retractable::Retractable() : extended(false) {
    std::cout << "made Retractable@" << ((void*) this) << std::endl;
  }    
  Retractable::~Retractable() {}
  void Retractable::extend() { extended = true; }
  void Retractable::retract() { extended = false; }
  bool Retractable::isExtended() const { return extended; }
}
