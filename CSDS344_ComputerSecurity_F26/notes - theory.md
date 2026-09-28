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


## Mechanisms of Keys

* Kerckhoff's Principle - security of cipher is based on secrecy of key, not secrecy of algorithm
* need to generate random bits (keys), often called a pseudo-radom number generator (PRNG)
* Forward   Secrecy - If long term key is exposesd, previous communication shouldn't be exposed (use session keys)
* Computational Security - an encryption system is considered secure because breaking it takes more time and computer power than an attacker can realistically afford. 
    * A larger key size means a vastly larger number of possible combinations. But larger keys size doesn't necessarily mean higher security level.

**Generating Keys**
1. Random - using a PRNG
2. From a Password - Key derivation function. ex. PBKDF --> adds a salt (random unique string to the password), hashes this password+salt by applying a pseudorandom function a thousand or million times --> hackers will need massive time & processing power to guess a single password.
3. Key Agreement Protocol - 2 or more parties jointly create a shared secret session key so that every participant influences the final value

**Protecting Keys**
1. Key Wrapping - Encrypt with another key
2. From a Password 
3. Store in hardware

## Encryption Techniques

**Key Definitions**
* Block - fixed-length group of bits
* Block Size - Small block sizes mean that repeating data patterns show up in the ciphertext after encrypting a relatively small amount of data
* Key Length - : A longer key creates a massive number of possible combinations (the key space), making it computationally harder for computers to guess the key.
* "can run in parallel" - The ability of an encryption or decryption algorithm to process multiple data blocks or segments at the same time across multiple processor cores or hardware pipelines to increase speed
* Encoding vs Encryption: encryption is for security; encoding is on changing data into a format compatible for sharing/usability (ex. Base64 formats binary files to send safely in emails, URL encoding, ASCII).

**Symmetric Encryption**
* same shared key used for both encryption & decryption; way faster than asymmetric. Both parties must know the key.

* Types of Symmetric Encryption
    - Block Cipher - Encrypts data in fixed-size blocks. If final data chunk is short, padding is added. Uses multiple rounds of substition & permutation driven by a secret key.
    - Stream Cipher - Encrypts data continuously, handling one bit or one byte at a time. primarily used in hardware.

* Popular Algorithms
    - Data Encryption Standard (DES/3DES) - DONT USE; these are obsolete & disallowed because its small key lengths & block sizes make them vulnerable to modern attacks.
    - AES (Advanced Encryption Standard): USE. 128, 192, 256 key sizes.
    - CBC (Cipher Block Chaining) - USE.
    - GCM (Galois Counter Mode) - USE. 
    - Blowfish: DON'T USE; small 64-bit block size
    - RC4 - DON'T USE.
    - ECB (Electronic Code Block) - DON'T USE

* Initialization Vector (IV) - random/pseudo-random value used with a secret key to add randomness
    

**Asymmetric Encryption**
Matt wants to send a message to Jane. 
1. Jane creates her public & private key pair.
2. Jane gives her public key to Matt.
3. Matt writes a message for Jane & locks it using Jane's public key.
4. Matt sends the locked message (usually through the internet).
5. Jane unlocks the messsage using her secret private key.

* You can generate the public key from the private.

* Popular Algorithms
    - RSA (Rivest, Shamir, Adleman): 2048 minimum
    - ECC (Elliptic Curve Cryptography): 
    - DSS (Digital Signature Standard): enhanced with ECDSA
    - ECDH (Diffie-Hellman Key Exchange): used for exchanging session keys

## Digital Signatures
* guarantees who sent the message (authentication)
* Fundamentally: hash with asymmetric encryption
* General Formula: DS = Encrypt (Hash(mssage), private key)


## Certificates


# Network Security

## Quiz Questions
**IP Addresses**
* Private: Starts with 10., 172.16 - 172.31, 192.168
* Loopback/own computer: 127.0.0.1

**Securing a network**
Protecting the network & everything connected to or communicating through it.
* Network Devices - routers, switches, firewalls, etc.
* Hosts - computers, servers, phones, etc.
* Applications - websites, email, databases, etc.
* Protocols - HTTP,DNS, TCP/IP, SSH, and Wi-Fi protocols.

**Denial of Service attack**
* Affects availability
* Indication that DoS occuurred: unable to access website.

## Network
## Internet
The internet is a network of networks. It is made of standards/protocols, addressing, reliable communication,and more.


## OSI Layers


