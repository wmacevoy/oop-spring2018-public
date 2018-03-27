#pragma once

namespace powerstrip {
    class SolarPanel {
    public: SolarPanel();
    public: virtual void pointAtSun();
    public: virtual double amps() const;
    public: virtual ~SolarPanel();
    };
}
