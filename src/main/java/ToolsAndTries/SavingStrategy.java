package ToolsAndTries;

import RISK.RiskModel;

public interface SavingStrategy {
    void saveGame(RiskModel model, String path);
}
