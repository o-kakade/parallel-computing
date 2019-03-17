# Breaking RSA

Program to compute modular cube root(s) in parallel on the GPU using a brute force search.

RSA cyphertext encryption


c = m<sup>3</sup>(mod n)

where c = ciphertext
m = plaintext message


Can decrypt without the private decryption key by computing a modular cube root

m = c <sup>1/3</sup> (mod n)

Given c and modulus n, the program finds all cube roots of c (mod n).

## Output Example

```
$ java pj2 ModCubeRoot 46054145 124822069
142857^3 = 46054145 (mod 124822069)
27549958^3 = 46054145 (mod 124822069)
97129254^3 = 46054145 (mod 124822069)
```
