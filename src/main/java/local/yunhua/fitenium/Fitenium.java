package local.yunhua.fitenium;

import local.yunhua.fitenium.job.Job;


public class Fitenium {

    public static void main(String[] args) {

        if (0 == args.length) {
            return;
        }

        String type = args[0];

        for(int index = 1; index < args.length; index++) {
            Job job = new Job();
            job.run(type, args[index]);

        }

    }

}
