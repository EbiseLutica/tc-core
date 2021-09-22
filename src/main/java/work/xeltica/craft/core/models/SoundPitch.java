package work.xeltica.craft.core.models;

import lombok.Getter;

public enum SoundPitch {
    F_0(0.5f),
    G0(0.529731547179648f),
    G_0(0.561231024154687f),
    A0(0.594603557501361f),
    A_0(0.629960524947437f),
    B0(0.667419927085017f),
    C1(0.707106781186548f),
    C_1(0.749153538438341f),
    D1(0.7937005259841f),
    D_1(0.840896415253715f),
    E1(0.890898718140339f),
    F1(0.943874312681694f),
    F_1(1f),
    G1(1.0594630943593f),
    G_1(1.12246204830937f),
    A1(1.18920711500272f),
    A_1(1.25992104989487f),
    B1(1.33483985417003f),
    C2(1.4142135623731f),
    C_2(1.49830707687668f),
    D2(1.5874010519682f),
    D_2(1.68179283050743f),
    E2(1.78179743628068f),
    F2(1.88774862536339f),
    F_2(2f),
    ;
    SoundPitch(float pitch) {
        this.pitch = pitch;
    }

    @Getter
    private final float pitch;
}
