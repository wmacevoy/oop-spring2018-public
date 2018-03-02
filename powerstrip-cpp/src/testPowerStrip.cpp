#include <gtest/gtest.h>
#include <stdexcept>

#include "PowerStrip.h"

using namespace powerstrip;

TEST(PowerStrip, Construct) {
  for (int outlets = -1; outlets <= 4; ++outlets) {
    try {
      PowerStrip::Ptr ps(new PowerStrip(outlets));
      ASSERT_EQ(outlets,ps->getOutlets());
      ASSERT_EQ(Mode::REMOTE,ps->getMode());
      for (int i=-1; i<=ps->getOutlets(); ++i) {
        try {
          ASSERT_EQ(OutletState::UNPOWERED, ps->getOutletState(i));
        } catch (std::out_of_range &ex) {
          ASSERT_TRUE(i < 0 || i >= ps->getOutlets());

        }
      }
    } catch (std::out_of_range &ex) {
      ASSERT_TRUE(outlets <= 0);
    }
  }
}

TEST(PowerStrip, ModeOn) {
  int outlets = 4;
  powerstrip::PowerStrip ps(outlets);
  ps.setMode(powerstrip::Mode::ON);
  for (int outlet = 0; outlet < ps.getOutlets(); ++outlet) {
    ps.setOutletState(outlet,powerstrip::OutletState::POWERED);
    ASSERT_EQ(powerstrip::OutletState::POWERED,ps.getOutletState(outlet));
    ps.setOutletState(outlet,powerstrip::OutletState::UNPOWERED);
    ASSERT_EQ(powerstrip::OutletState::POWERED,ps.getOutletState(outlet));
  }
}

TEST(PowerStrip, ModeOff) {
  int outlets = 4;
  powerstrip::PowerStrip ps(outlets);
  ps.setMode(powerstrip::Mode::OFF);
  for (int outlet = 0; outlet < ps.getOutlets(); ++outlet) {
    ps.setOutletState(outlet,powerstrip::OutletState::POWERED);
    ASSERT_EQ(powerstrip::OutletState::UNPOWERED,ps.getOutletState(outlet));
    ps.setOutletState(outlet,powerstrip::OutletState::UNPOWERED);
    ASSERT_EQ(powerstrip::OutletState::UNPOWERED,ps.getOutletState(outlet));
  }
}

TEST(PowerStrip, ModeRemote) {
  int outlets = 4;
  powerstrip::PowerStrip ps(outlets);
  ps.setMode(powerstrip::Mode::REMOTE);
  for (int outlet = 0; outlet < ps.getOutlets(); ++outlet) {
    ps.setOutletState(outlet,powerstrip::OutletState::POWERED);
    ASSERT_EQ(powerstrip::OutletState::POWERED,ps.getOutletState(outlet));
    ps.setOutletState(outlet,powerstrip::OutletState::UNPOWERED);
    ASSERT_EQ(powerstrip::OutletState::UNPOWERED,ps.getOutletState(outlet));
  }
}





