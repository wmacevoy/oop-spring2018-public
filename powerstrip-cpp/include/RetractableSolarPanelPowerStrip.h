#pragma once

#include "RetractableSolarPanel.h"
#include "RetractablePowerStrip.h"

namespace powerstrip {
    class RetractableSolarPanelPowerStrip : public RetractableSolarPanel, public RetractablePowerStrip {
    public: RetractableSolarPanelPowerStrip(int outlets);
    };

}