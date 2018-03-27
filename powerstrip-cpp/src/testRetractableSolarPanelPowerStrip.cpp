//
// Created by user on 3/22/18.
//

#include <gtest/gtest.h>
#include <stdexcept>
#include <iostream>
#include "RetractableSolarPanelPowerStrip.h"

using namespace powerstrip;

void retractPowerStrip(RetractablePowerStrip &rps) {
  rps.retract();
}

void extendPowerStrip(RetractablePowerStrip &rps) {
  std::cout << "Extending powerstrip@" << ((void*) &rps) << std::endl;
  rps.extend();
}

bool isExtendedPowerStrip(RetractablePowerStrip &rps) {
  return rps.isExtended();
}

void retractSolarPanel(RetractableSolarPanel &rsp) {
  rsp.retract();
}

void extendSolarPanel(RetractableSolarPanel &rsp) {
  std::cout << "Extending solar panel@" << ((void*) &rsp) << std::endl;  
  rsp.extend();
}

bool isExtendedSolarPanel(RetractableSolarPanel &rsp) {
  return rsp.isExtended();
}

TEST(RetractableSolarPanelPowerStrip, Construct) {
    RetractableSolarPanelPowerStrip rspps(4);

    retractPowerStrip(rspps);
    ASSERT_FALSE(isExtendedPowerStrip(rspps));
    extendPowerStrip(rspps);
    ASSERT_TRUE(isExtendedPowerStrip(rspps));

    retractSolarPanel(rspps);
    ASSERT_FALSE(isExtendedSolarPanel(rspps));
    extendSolarPanel(rspps);
    ASSERT_TRUE(isExtendedSolarPanel(rspps));

    retractPowerStrip(dynamic_cast<RetractablePowerStrip&>(rspps));
    ASSERT_FALSE(isExtendedSolarPanel(rspps));
    extendPowerStrip(rspps);    
    ASSERT_TRUE(isExtendedSolarPanel(rspps));
    
}
