#pragma once

#include "SolarPanel.h"
#include "Retractable.h"

namespace powerstrip {
    class RetractableSolarPanel : public Retractable, public SolarPanel {
    private:
        bool extended;

        virtual void extend() override;

        virtual void retract() override;

        virtual bool isExtended() const override;

        double amps() const override;
    };
}