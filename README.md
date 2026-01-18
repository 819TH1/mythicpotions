# MythicPotions

MythicMobs に LoL (League of Legends) 風の強力なクラウドコントロール（CC）を追加する拡張プラグインです。

## 追加されるメカニック

### 1. Knockup (ノックアップ)
ターゲットを真上に打ち上げ、一定時間行動不能にします。
空中で静止させる時間を設定できるため、より強力な演出が可能です。

- **構文:** `- knockup{duration=20;height=2} @target`
- **オプション:**
  - `duration` (d): 空中に留まる時間 (Ticks)
  - `height` (h): 打ち上げる高さ

### 2. Charm (チャーム)
ターゲットをキャスター（スキル使用者）の方へ強制的に歩かせます。
アーリのスキルのように、敵を引き寄せるトリッキーな動きを再現できます。

- **構文:** `- charm{duration=40;speed=0.15} @target`
- **オプション:**
  - `duration` (d): 魅了されている時間 (Ticks)
  - `speed` (s): 引き寄せられる速度 (デフォルト: 0.12)

## 導入方法

1. 右側の [Releases](https://github.com/819TH1/mythicpotions/releases) または [Actions](https://github.com/819TH1/mythicpotions/actions) から最新の JAR ファイルをダウンロードします。
2. サーバーの `plugins` フォルダに JAR を入れます。
3. MythicMobs のスキル設定（YAML）で上記のメカニックを使用してください。

## 開発者向け (Build)
このプロジェクトは GitHub Actions による自動ビルドに対応しています。
ソースコードを Push すると、自動的に JAR ファイルが生成されます。