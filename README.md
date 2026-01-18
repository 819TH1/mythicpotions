# MythicPotions

## 追加されるメカニック

### 1. Knockup (ノックアップ)
ターゲットを真上に打ち上げ、一定時間行動不能にします。

- **構文:** `- knockup{duration=20;height=2} @target`
- **オプション:**
  - `duration` (d): 空中に留まる時間 (Ticks)
  - `power` (p): 打ち上げる強さ

### 2. Charm (チャーム)
ターゲットをキャスター（スキル使用者）の方へ強制的に歩かせます。
- **構文:** `- charm{duration=40;speed=0.15} @target`
- **オプション:**
  - `duration` (d): 魅了されている時間 (Ticks)
  - `speed` (s): 引き寄せられる速度 (デフォルト: 0.12)

## 導入方法

1. 右側の [Releases](https://github.com/819TH1/mythicpotions/releases) または [Actions](https://github.com/819TH1/mythicpotions/actions) から最新の JAR ファイルをダウンロードします。
2. サーバーの `plugins` フォルダに JAR を入れます。
3. MythicMobs のスキル設定（YAML）で上記のメカニックを使用してください。