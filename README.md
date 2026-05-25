# Create: Diesel Generators — Sky Islands

A small compatibility mod for **NeoForge 1.21.1** that lets [Create Diesel Generators](https://www.curseforge.com/minecraft/mc-mods/create-diesel-generators) pumpjacks work in sky island worlds (e.g. Sky Archipelago + Aeronautics).

## The problem

CDG pumpjacks validate their pipe column by scanning downward until they find a block tagged `createdieselgenerators:oil_deposit`. In a normal world that tag includes bedrock, so the pipe just has to reach Y = −64. In a sky island world there is no bedrock, so the pumpjack never becomes valid — even though oil data is still generated per-chunk via Perlin noise and pumping would work fine if the validity check passed.

## The fix

This mod adds a craftable **Sky Oil Deposit** block that is included in CDG's `oil_deposit` tag. Place it at the bottom of the pipe column instead of bedrock. CDG treats it identically to bedrock and the pumpjack will start pumping.

Oil amounts are still determined by CDG's normal chunk-based noise — this block does not create oil, it only satisfies the depth check.

## Sky Oil Deposit

| Property | Value |
|---|---|
| Hardness | 5.0 |
| Blast resistance | 6.0 |
| Required tool | Diamond pickaxe or better |

### Recipe

```
[ Ender Eye ] [ Brass Casing ] [ Ender Eye ]
[ Brass Casing ] [ Ender Eye ] [ Brass Casing ]
[ Ender Eye ] [ Brass Casing ] [ Ender Eye ]
```

5 Eyes of Ender + 4 Brass Casings → 1 Sky Oil Deposit

## Dependencies

| Mod | Version |
|---|---|
| NeoForge | 21.1.x |
| Create | 6.0.x |
| Create Diesel Generators | 1.21.1-1.3.0+ |
