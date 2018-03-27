#include "SolarPanel.h"

#include <iostream>

namespace powerstrip {
  SolarPanel::SolarPanel() {
    std::cout << "made SolarPanel@" << ((void*) this) << std::endl;
  }
    void SolarPanel::pointAtSun() {
        std::cout << "pointing at sun..." << std::endl;
    }

    double SolarPanel::amps() const {
        return 1.0;
    }

    SolarPanel::~SolarPanel() {}
}

