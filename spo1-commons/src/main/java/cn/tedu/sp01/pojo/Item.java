package cn.tedu.sp01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.management.counter.perf.PerfInstrumentation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Integer id;
    private String name;
    private Integer number;
}
