#include "RetractableSolarPanel.h"

namespace powerstrip {
    void RetractableSolarPanel::extend()  { extended = true; }

    void RetractableSolarPanel::retract()  { extended = false; }

    bool RetractableSolarPanel::isExtended() const  { return extended; }

    double RetractableSolarPanel::amps() const  {
        return extended ? 1.0 : 0.0;
    }
}
