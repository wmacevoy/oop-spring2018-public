#include <gtest/gtest.h>

#include <string>

class Hello {
private: const std::string who;
public: Hello(const std::string &_who) : who(_who) {}
public: std::string str() const {
  std::string msg;
  msg.append("hello, ");
  msg.append(who);
  return msg;
}
};

TEST(HelloTest, Main) {
  Hello hello("world");
  ASSERT_EQ("hello, world", hello.str());
}

int main(int argc, char **argv) {
  ::testing::InitGoogleTest(&argc, argv);
  return RUN_ALL_TESTS();
}



  
