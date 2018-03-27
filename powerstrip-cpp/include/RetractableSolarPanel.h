#pragma once

#include "SolarPanel.h"
#include "Retractable.h"

namespace powerstrip {
    class RetractableSolarPanel : public virtual Retractable, public SolarPanel {
    public:  RetractableSolarPanel();
    public: double amps() const override;
    };
}
