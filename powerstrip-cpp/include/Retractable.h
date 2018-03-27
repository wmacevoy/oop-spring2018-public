#pragma once

namespace powerstrip {
    class Retractable {
    protected: bool extended;
    public:  Retractable();
    public:  virtual void extend();
    public:    virtual void retract();
    public:    virtual bool isExtended() const;
    public:    virtual ~Retractable();
    };
}
