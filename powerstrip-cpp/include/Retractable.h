#pragma once

namespace powerstrip {
    struct Retractable {
        virtual void extend() = 0;

        virtual void retract() = 0;

        virtual bool isExtended() const = 0;

        virtual ~Retractable();
    };
}