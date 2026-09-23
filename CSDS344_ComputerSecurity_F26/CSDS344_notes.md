# VMs & Ubuntu
VMs & Ubuntu give you a safe, standardized lab environment  where you can actually do security work without risking your device.

# Cryptography
* developing and using coded algorithms to protect and obscure transmitted information so that it may only be read by those with the permission and ability to decrypt it.
**Use Cases**
* secure traffic from web browser to web server
* secure authentication (proving identities)
* digital signatures -  proving who people are or things have not been modified
* Non repudiation - proving that this is from a person and they can't deny it's from them


* Random Numbers - Needed for generating keys, adding randomness; Pseudo-random Number Generator (PRNG)
* Hashes (Message Digests) - Used for fingerprint of data, signatures
* Symmetric Encryption - Ensures confidentiality with shared key, fast
* Asymmetric Encryption - Ensures confidentiality with public/private key, slower
* Digital Signatures - Ability to verify data based on a secret the signer knows
* Digital Certificates - Ability to verify signer based on trust in a root certificate
* Key Exchange - Sharing a secret over an open channel

**Intro to VI**
* VI is a text editor that runs inside the terminal on Unix/Linux systems.
* ex: `vi notes.txt`

Vi Modes:
* Normal mode = move around, delete, copy, save, quit
* Insert mode = actually type text
* Command mode = admin task (save/quit); default start

Sample workflow:
`vi notes.txt` --> opens `notes.txt`; if not exists, `vi` can create it.
* Press `I` to enter insert mode then type your text. Press `Esc` to leave insert mode.
* To save, type: `:w`
* To quit, type: `:q`
* To save & quit, type: `:wq`
* Quit without saving: `Esc :wq!`

**Kerckhoffs's Principle**
Security should depend on the secrecy of the key, not on the secrecy of the algorithm.
* The encryption algorithm can be publicly known but the cryptographic key must remain secret.
* **Cryptographic Randomness**
* Cryptographic systems need to generate random bits securely.
* A **Pseudo-Random Number Generator or PRNG** is commonly used to generate random-looking bits & cryptographic keys.
* A cryptographic PRNG must produce output that attackers cannot reasonably predict.
* **Keys are central to cryptographic security.**
* A strong encryption algorithm is not enough if the key is weak or predictable.
* Secure cryptography requires a strong algorithm, a strong key, secure key generation, and secure key protection.
* **Forward Secrecy**
* Forward secrecy means that exposing a long-term key should not expose previous communications.
* Forward secrecy commonly uses temporary **session keys**.
* A long-term key may exist for identity or authentication purposes.
* A session key is temporary and may only be used for one connection or communication session.
* If a long-term key is compromised later, previous session keys should not automatically be recoverable.
* The goal of forward secrecy is that **key compromise today should not reveal communications from the past.**
* **Computational Security**
* Computational security means choosing algorithms and security levels so that decrypting data without the key would take an impractical amount of computation.
* Modern cryptography usually does not claim that breaking encryption is mathematically impossible.
* Instead, it aims to make attacks computationally infeasible.
* A cryptosystem is considered practically secure when the best known attack would require an unreasonable amount of time or computing power.
* **Key Size is not the same as Security Level.**
* A key containing a certain number of bits does not automatically provide that many bits of security.
* Security level represents approximately how much computational effort is required by the best known attack.
* **RSA 1024** has a 1024-bit key but provides only about **70-bit security** according to the lecture.
* **AES 128** has a 128-bit key and provides approximately **128-bit security**.
* RSA and AES cannot be compared simply by looking at their key lengths because they rely on different mathematical problems.
* **128-bit security** roughly means an attack would require work on the order of about 2 raised to the 128 operations.
* **256-bit security** refers to the security level, not simply having a 256-bit key.
* **Generating Cryptographic Keys**
* The lecture gives three main approaches to generating or establishing keys.
* **1. Random Generation**
* Generate a key directly using secure random bits from a PRNG.
* Random key generation can provide high entropy when the PRNG is secure.
* **2. Generate a Key from a Password**
* Human passwords are usually not suitable for direct use as cryptographic keys.
* A **Key Derivation Function or KDF** converts a password into cryptographic key material.
* The lecture specifically mentions **PBKDF** as a key derivation approach that will be used in the course.
* The basic process is **password to PBKDF to cryptographic key**.
* **3. Key Agreement Protocol**
* A key agreement protocol allows two parties to establish a shared secret key.
* The lecture gives **Diffie-Hellman** as an example.
* Diffie-Hellman will be discussed later in the course.
* **Key Generation Summary**
* Random generation uses a **PRNG**.
* Password-based generation uses a **PBKDF or another key derivation function**.
* Key agreement can use a protocol such as **Diffie-Hellman**.
* **Protecting Cryptographic Keys**
* Generating a strong key is not enough because the key itself must also be protected.
* If an attacker obtains both the encrypted data and the encryption key, the encryption may provide little protection.
* The lecture gives three main approaches to protecting keys.
* **1. Key Wrapping**
* Key wrapping means encrypting one cryptographic key using another key.
* The protected key is stored in encrypted form instead of being stored directly.
* This may create a hierarchy where one key protects another key.
* **2. Generate the Key from a Password**
* Instead of permanently storing a key directly, the system can derive the key from a password when needed.
* The password therefore becomes an important security secret.
* **3. Store the Key in Hardware**
* Cryptographic keys can be stored in dedicated secure hardware.
* Hardware storage is more expensive but can provide stronger protection.
* The goal is to prevent normal software or attackers from easily extracting the secret key.
* **Overall Key Lifecycle**
* First, a key must be **generated or established securely**.
* The key can be created randomly, derived from a password, or established through a key agreement protocol.
* The key is then used for cryptographic operations such as encryption.
* The key itself must then be protected through techniques such as key wrapping, password derivation, or secure hardware.
* Temporary session keys can also be used to provide forward secrecy and reduce the damage caused by future key compromise.
* **Main Relationship Between the Concepts**
* Kerckhoffs's Principle says that the **key must be the secret**, not the algorithm.
* Because the key is the main secret, the system needs secure ways to **generate keys**.
* Secure key generation requires unpredictable randomness or secure derivation and agreement mechanisms.
* Once generated, keys must also be **protected from attackers**.
* Algorithms and key sizes must provide enough **computational security** that attacks without the key are impractical.
* **Forward secrecy** reduces the damage if a long-term key is eventually compromised.
* **Core Exam Concepts**
* **Kerckhoffs's Principle:** Security depends on keeping the key secret, not the algorithm.
* **PRNG:** Securely generates random-looking bits used for keys.
* **Forward Secrecy:** Compromise of a long-term key should not reveal previous communications.
* **Computational Security:** Breaking encryption without the key should require impractical computational effort.
* **Key Size != Security Level:** The number of bits in a key does not necessarily equal the number of bits of security.
* **RSA 1024:** About 70-bit security according to the lecture.
* **AES 128:** About 128-bit security.
* **Key Generation:** Random PRNG, password plus PBKDF, or key agreement such as Diffie-Hellman.
* **Key Protection:** Key wrapping, password-derived keys, or hardware storage.




# Network Security
**Exercise**
* IP address
* ports open
* versions of software
* Do a search on `xz utils vulnerability` - a data compression library
```
sudo apt install traceroute
tcp traceroute caslab.cade.edu  

```