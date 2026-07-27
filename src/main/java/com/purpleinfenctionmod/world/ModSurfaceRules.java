package com.purpleinfenctionmod.world;

// import com.purpleinfenctionmod.block.ModBlocks;
// import net.minecraft.block.Blocks;
// import net.minecraft.util.math.VerticalSurfaceType;
// import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModSurfaceRules {
    // private static final MaterialRules.MaterialRule INFECTED_GRASS = MaterialRules.block(ModBlocks.INFECTED_GRASS.getDefaultState());
    // private static final MaterialRules.MaterialRule INFECTED_DIRT = MaterialRules.block(ModBlocks.INFECTED_DIRT.getDefaultState());
    // private static final MaterialRules.MaterialRule GRAVEL = MaterialRules.block(Blocks.GRAVEL.getDefaultState());
    // private static final MaterialRules.MaterialRule STONE = MaterialRules.block(Blocks.STONE.getDefaultState());

    // public static MaterialRules.MaterialRule makeRules() {
    //     // 1. Строго первый верхний блок поверхности (глубина 0, без расширения шумом)
    //     MaterialRules.MaterialCondition topBlock = MaterialRules.stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR);
        
    //     // 2. Слой грунта под первым блоком (отступ 1 блок вниз, расширение шумом включено, толщина до 3 блоков)
    //     MaterialRules.MaterialCondition dirtLayer = MaterialRules.stoneDepth(1, true, 3, VerticalSurfaceType.FLOOR);

    //     // 3. Блокировка пещер: hole() проверяет наличие блоков (потолка) над текущей точкой. 
    //     // not(hole()) гарантирует, что мы находимся под открытым небом.
    //     MaterialRules.MaterialCondition notInCave = MaterialRules.not(MaterialRules.hole());

    //     // 4. Проверка на сушу (уровень выше воды)
    //     MaterialRules.MaterialCondition isAboveWater = MaterialRules.water(-1, 0);

    //     // Логика сборки слоев для суши
    //     MaterialRules.MaterialRule landRule = MaterialRules.sequence(
    //         MaterialRules.condition(topBlock, INFECTED_GRASS), // Срабатывает только для Y=0 относительно поверхности
    //         MaterialRules.condition(dirtLayer, INFECTED_DIRT)  // Срабатывает для Y от -1 до -4 относительно поверхности
    //     );

    //     // Логика сборки слоев для водоемов
    //     MaterialRules.MaterialRule underwaterRule = MaterialRules.sequence(
    //         MaterialRules.condition(topBlock, GRAVEL),
    //         MaterialRules.condition(dirtLayer, STONE)
    //     );

    //     // Финальное правило
    //     return MaterialRules.condition(
    //         MaterialRules.biome(ModBiomes.INFECTED_KEY), // Применяем только в вашем биоме
    //         MaterialRules.condition(
    //             notInCave, // Игнорируем пещеры и шахты глобально
    //             MaterialRules.sequence(
    //                 MaterialRules.condition(isAboveWater, landRule), // Если суша -> используем landRule
    //                 underwaterRule // Если вода -> используем underwaterRule
    //             )
    //         )
    //     );
    // }
}