#include "SolarPanel.h"

#include <iostream>

namespace powerstrip {
    void SolarPanel::pointAtSun() {
        std::cout << "pointing at sun..." << std::endl;
    }

    double SolarPanel::amps() const {
        return 1.0;
    }

    SolarPanel::~SolarPanel() {}
}

