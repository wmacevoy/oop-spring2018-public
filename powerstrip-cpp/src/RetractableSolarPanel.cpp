#include <iostream>
#include "RetractableSolarPanel.h"

namespace powerstrip {
  RetractableSolarPanel::RetractableSolarPanel() {
    std::cout << "made RetractableSolarPanel@" << ((void*) this) << std::endl;
  }

    double RetractableSolarPanel::amps() const  {
        return extended ? 1.0 : 0.0;
    }
}
