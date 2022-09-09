# Simple simulations

## Development

Interactive development

```
nix develop --command yarn install
nix develop --command clj -M:shadow-cljs watch app
```

Update dependencies

```
nix develop --command clj2nix deps.edn deps.nix
```

## Reference

* [Simple Simulations for System Builder](https://brooker.co.za/blog/2022/04/11/simulation.html)
  [(code)](https://github.com/mbrooker/simulator_example)
* [Serial, Parallel, and Quorum Latencies](https://brooker.co.za/blog/2021/10/20/simulation.html)
