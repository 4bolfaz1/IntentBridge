# IntentBridge

**IntentBridge** is a lightweight Android utility that enables sending `Broadcast` Intents via URI links. Its main purpose is to allow web pages and other URI-capable sources to trigger broadcasts — something Android does not normally support through standard web links, which can only launch activities.

---

## URI Format

IntentBridge uses a custom URI scheme: `bridge://`. The format is as follows:

```
bridge://<targetPackage>/[c/<receiverClass>]/[a/<action>]?key1=value1&key2=value2
```

### URI Components

| Segment           | Description                                            | Example                              |
| ----------------- | ------------------------------------------------------ | ------------------------------------ |
| `bridge://`       | Custom URI scheme identifier                           | `bridge://`                          |
| `<targetPackage>` | Target app's package name                              | `com.example.app`                    |
| `/c/...`          | *(Optional)* Fully-qualified `BroadcastReceiver` class | `c/com.example.app.MyReceiver`       |
| `/a/...`          | *(Optional)* Broadcast action string                   | `a/com.example.app.ACTION_SEND_DATA` |
| `?key=value`      | *(Optional)* Extras passed as query parameters         | `?user=Jack&flag=true`               |

> You may include only the class, only the action, or both.

---

## Usage Scenarios

### 1. Web Pages

A `bridge://` URI can be embedded as a hyperlink in any HTML content. When opened on Android, the system prompts the user to handle it with a compatible app like IntentBridge:

```html
<a href="bridge://com.example.app/a/com.example.app.ACTION_TEST?foo=bar">Trigger Action</a>
```

This enables both remote and local web content to send broadcast intents.

> ⚠️ Be cautious: some websites may attempt to misuse this feature. Only interact with trusted sources.

### 2. Creating Shortcuts in IntentBridge

The IntentBridge app includes a built-in form to create shortcuts. You’ll be asked to fill in:

* **Shortcut ID** — A unique identifier for the shortcut
* **Shortcut Label** — The name shown in the system shortcut list
* **Bridge URI** — The full broadcast URI

After saving, the shortcut becomes accessible by long-pressing the IntentBridge app icon on the launcher. It is not automatically pinned to the home screen.

> Shortcut IDs must be unique to avoid overwriting existing entries.

### 3. Other Uses

Bridge URIs can also be used in:

* QR codes or NFC tags
* Automation tools (e.g., Tasker, MacroDroid)
* Custom launchers or widgets that support URI-based actions

---

## URI Examples

* **Class only**

  ```
  bridge://com.example.app/c/com.example.app.MyReceiver
  ```

* **Action with Extras**

  ```
  bridge://com.example.app/a/com.example.app.ACTION_TEST?user=Jack
  ```

* **Class and Action**

  ```
  bridge://com.example.app/c/com.example.app.MyReceiver/a/com.example.app.ACTION_TEST
  ```

---

## License

IntentBridge is licensed under the **GNU General Public License v3.0 (GPL-3.0)**. You are free to use, modify, and redistribute this software under the terms of the license.

For full details, see the [LICENSE](LICENSE) file.
