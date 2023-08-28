# generated by clj2nix-1.1.0-rc
{ fetchMavenArtifact, fetchgit, lib }:

let repos = [
        "https://repo1.maven.org/maven2/"
        "https://repo.clojars.org/" ];

  in rec {
      makePaths = {extraClasspaths ? []}:
        if (builtins.typeOf extraClasspaths != "list")
        then builtins.throw "extraClasspaths must be of type 'list'!"
        else (lib.concatMap (dep:
          builtins.map (path:
            if builtins.isString path then
              path
            else if builtins.hasAttr "jar" path then
              path.jar
            else if builtins.hasAttr "outPath" path then
              path.outPath
            else
              path
            )
          dep.paths)
        packages) ++ extraClasspaths;
      makeClasspaths = {extraClasspaths ? []}:
       if (builtins.typeOf extraClasspaths != "list")
       then builtins.throw "extraClasspaths must be of type 'list'!"
       else builtins.concatStringsSep ":" (makePaths {inherit extraClasspaths;});
      packageSources = builtins.map (dep: dep.src) packages;
      packages = [
  rec {
    name = "transit-java/com.cognitect";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "transit-java";
      groupId = "com.cognitect";
      sha512 = "294725b1003323981d1ffa0a6952fbe152e7704b2cbef91c848958df56d0b4d8642eae717398d9859d8e04d77ccebc64e238db2b12d637b6fef43c25a2191999";
      version = "1.0.362";

    };
    paths = [ src ];
  }

  rec {
    name = "data.json/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "data.json";
      groupId = "org.clojure";
      sha512 = "04b7c0c90cb26d643a0b3e7e1ffa2d2d423e977c1454ee5ea7c2e75547ecbc113838df17b797902a975f5ea2184a81a45b605a4d82970805e2bbb02feebc578d";
      version = "2.4.0";

    };
    paths = [ src ];
  }

  rec {
    name = "clojure/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "clojure";
      groupId = "org.clojure";
      sha512 = "1925300a0fe4cc9fc3985910bb04ae65a19ce274dacc5ec76e708cfa87a7952a0a77282b083d0aebb2206afff619af73a57f0d661a3423601586f0829cc7956b";
      version = "1.11.1";

    };
    paths = [ src ];
  }

  rec {
    name = "shadow-cljsjs/thheller";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "shadow-cljsjs";
      groupId = "thheller";
      sha512 = "3d0b79eff075c38389d9f3501c60bf91a28a6ee25fc0a2df3159b365eff556e2ec8499b69860783478705ea33ff18e85fc0150229d5725e58e96f362bdc777f1";
      version = "0.0.22";

    };
    paths = [ src ];
  }

  rec {
    name = "commons-codec/commons-codec";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "commons-codec";
      groupId = "commons-codec";
      sha512 = "da30a716770795fce390e4dd340a8b728f220c6572383ffef55bd5839655d5611fcc06128b2144f6cdcb36f53072a12ec80b04afee787665e7ad0b6e888a6787";
      version = "1.15";

    };
    paths = [ src ];
  }

  rec {
    name = "tools.analyzer/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "tools.analyzer";
      groupId = "org.clojure";
      sha512 = "c51752a714848247b05c6f98b54276b4fe8fd44b3d970070b0f30cd755ac6656030fd8943a1ffd08279af8eeff160365be47791e48f05ac9cc2488b6e2dfe504";
      version = "1.1.0";

    };
    paths = [ src ];
  }

  rec {
    name = "cljs-test-display/com.bhauman";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "cljs-test-display";
      groupId = "com.bhauman";
      sha512 = "d8aafc0214bb3719adbb2c957e6a7c939e0db65a0bf69c4369654f736882c3203a4eeb66670e1da18f60faa8bf97b094712472ce883a5a1a5f8dd52b8e413d96";
      version = "0.1.1";

    };
    paths = [ src ];
  }

  rec {
    name = "core.specs.alpha/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "core.specs.alpha";
      groupId = "org.clojure";
      sha512 = "f521f95b362a47bb35f7c85528c34537f905fb3dd24f2284201e445635a0df701b35d8419d53c6507cc78d3717c1f83cda35ea4c82abd8943cd2ab3de3fcad70";
      version = "0.2.62";

    };
    paths = [ src ];
  }

  rec {
    name = "undertow-core/io.undertow";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "undertow-core";
      groupId = "io.undertow";
      sha512 = "d1dcc1236ac98518318bea1ab99037c4f447d431319dc9fa8a9bc830c2c3cee0f7b804cfb5492e71e68e96a128f259059de8d6404237572c78643c8824818b9b";
      version = "2.2.4.Final";

    };
    paths = [ src ];
  }

  rec {
    name = "expound/expound";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "expound";
      groupId = "expound";
      sha512 = "810832065897f33fd105aaf84984ee994ec5d90fe60d1a772b083bc2b1c59ff8e9e368bc0f456468824a65694f58cd47149f40b95488947ec5e5e96b44d44d1d";
      version = "0.9.0";

    };
    paths = [ src ];
  }

  rec {
    name = "spec.alpha/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "spec.alpha";
      groupId = "org.clojure";
      sha512 = "ddfe4fa84622abd8ac56e2aa565a56e6bdc0bf330f377ff3e269ddc241bb9dbcac332c13502dfd4c09c2c08fe24d8d2e8cf3d04a1bc819ca5657b4e41feaa7c2";
      version = "0.3.218";

    };
    paths = [ src ];
  }

  rec {
    name = "tools.cli/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "tools.cli";
      groupId = "org.clojure";
      sha512 = "1d88aa03eb6a664bf2c0ce22c45e7296d54d716e29b11904115be80ea1661623cf3e81fc222d164047058239010eb678af92ffedc7c3006475cceb59f3b21265";
      version = "1.0.206";

    };
    paths = [ src ];
  }

  rec {
    name = "commons-fileupload/commons-fileupload";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "commons-fileupload";
      groupId = "commons-fileupload";
      sha512 = "a8780b7dd7ab68f9e1df38e77a5207c45ff50ec53d8b1476570d069edc8f59e52fb1d0fc534d7e513ac5a01b385ba73c320794c82369a72bd6d817a3b3b21f39";
      version = "1.4";

    };
    paths = [ src ];
  }

  rec {
    name = "tools.analyzer.jvm/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "tools.analyzer.jvm";
      groupId = "org.clojure";
      sha512 = "36ad50a7a79c47dea16032fc4b927bd7b56b8bedcbd20cc9c1b9c85edede3a455369b8806509b56a48457dcd32e1f708f74228bce2b4492bd6ff6fc4f1219d56";
      version = "1.2.2";

    };
    paths = [ src ];
  }

  rec {
    name = "wildfly-common/org.wildfly.common";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "wildfly-common";
      groupId = "org.wildfly.common";
      sha512 = "f99f23af23a1cc45035b87ab422affdb911769ee63dc5a1c9b3e7a39ebefee07542d2388118282b20113c196e28abce8f87cafc8b0213cdc692381edce035c46";
      version = "1.5.2.Final";

    };
    paths = [ src ];
  }

  rec {
    name = "json-simple/com.googlecode.json-simple";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "json-simple";
      groupId = "com.googlecode.json-simple";
      sha512 = "f8798bfbcc8ab8001baf90ce47ec2264234dc1da2d4aa97fdcdc0990472a6b5a5a32f828e776140777d598a99d8a0c0f51c6d0767ae1a829690ab9200ae35742";
      version = "1.1.1";

    };
    paths = [ src ];
  }

  rec {
    name = "transit-cljs/com.cognitect";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "transit-cljs";
      groupId = "com.cognitect";
      sha512 = "526d3331857586ab7e8edb78795c375aaafe6dc3da24706663918a7dc38e25db7d0f554c334ec3be0334050d59d8616bd9cd6c9a90cf4bb4b33b1e0ea294d29c";
      version = "0.8.269";

    };
    paths = [ src ];
  }

  rec {
    name = "google-closure-library/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "google-closure-library";
      groupId = "org.clojure";
      sha512 = "85e259bd189554659fdcb2f137c7de81a8aac97669b865254c59c713e6a8b79eb4272fa1444b9bfc5d1c8447140daa53aac74cacd21527e6186cf1ec0e776d32";
      version = "0.0-20211011-0726fdeb";

    };
    paths = [ src ];
  }

  rec {
    name = "shadow-cljs/thheller";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "shadow-cljs";
      groupId = "thheller";
      sha512 = "090a6b3dbb4973ebcaf17cc32b8d5d32f6bf5c5ed3cbd556c7cff97241831f077d7b727a30ee424367bb6eb22322c5599559a1437f4145042650344566ffb2d5";
      version = "2.19.0";

    };
    paths = [ src ];
  }

  rec {
    name = "clojurescript/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "clojurescript";
      groupId = "org.clojure";
      sha512 = "0a80e3b2e455f71ec2b88de115601e1971e88d7899d769568541925da433c55876ec1ce1f9916f9cc4a7de1be4da099782f848ee21c673d08f8277e529d5a74d";
      version = "1.11.51";

    };
    paths = [ src ];
  }

  rec {
    name = "commons-io/commons-io";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "commons-io";
      groupId = "commons-io";
      sha512 = "6af22dffaaecd1553147e788b5cf50368582318f396e456fe9ff33f5175836713a5d700e51720465c932c2b1987daa83027358005812d6a95d5755432de3a79d";
      version = "2.10.0";

    };
    paths = [ src ];
  }

  rec {
    name = "fipp/fipp";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "fipp";
      groupId = "fipp";
      sha512 = "0c3bf011d9eec32993ccdf66910f818b4b0d80513c2cfb1cf6fc9714ec3d01ec4485397c90a6a0a4c0e30261323eabf0c090251c49c05061ab701292c5ad4306";
      version = "0.6.26";

    };
    paths = [ src ];
  }

  rec {
    name = "jboss-logging/org.jboss.logging";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "jboss-logging";
      groupId = "org.jboss.logging";
      sha512 = "c17b8882481c0cb8fbcdf7ea33d268e2173b1bfe04be71e61d5f07c3040b1c33b58781063f8ebf27325979d02255e62d1df16a633ac22f9d08adeb5c6b83a32a";
      version = "3.4.1.Final";

    };
    paths = [ src ];
  }

  rec {
    name = "jackson-core/com.fasterxml.jackson.core";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "jackson-core";
      groupId = "com.fasterxml.jackson.core";
      sha512 = "a1bd6c264b9ab07aad3d0f26b65757e35ff47904ab895bb7f997e3e1fd063129c177ad6f69876907b04ff8a43c6b1770a26f53a811633a29e66a5dce57194f64";
      version = "2.8.7";

    };
    paths = [ src ];
  }

  rec {
    name = "asm/org.ow2.asm";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "asm";
      groupId = "org.ow2.asm";
      sha512 = "876eac7406e60ab8b9bd6cd3c221960eaa53febea176a88ae02f4fa92dbcfe80a3c764ba390d96b909c87269a30a69b1ee037a4c642c2f535df4ea2e0dd499f2";
      version = "9.2";

    };
    paths = [ src ];
  }

  rec {
    name = "shadow-util/thheller";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "shadow-util";
      groupId = "thheller";
      sha512 = "803a068159276f5abf6d9cfa25f5c446c4c66d2eaec78393910cb2175b63a734c7c7effaf3064789608b1cf3899c287611c2d170fd78621193acc1fbbff1d5da";
      version = "0.7.0";

    };
    paths = [ src ];
  }

  rec {
    name = "transit-js/com.cognitect";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "transit-js";
      groupId = "com.cognitect";
      sha512 = "bab8c657449a1d01e884923b77c5ef3d7a1a246c3ad1bd85a5b0c98b7952dbdeea97ce7d340ab50aa0dca647a1ef7eb846a52a1a4eecc55e1ff22145b8e820fe";
      version = "0.8.874";

    };
    paths = [ src ];
  }

  rec {
    name = "directory-watcher/io.methvin";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "directory-watcher";
      groupId = "io.methvin";
      sha512 = "bcd346c08d73980e05592690e3525889c241f878909c85d7e097c7f99f38c64693870b69a41bfc0b02a4749387cef45089554898cfec4df5fda43a48acb3a7d1";
      version = "0.15.1";

    };
    paths = [ src ];
  }

  rec {
    name = "google-closure-library-third-party/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "google-closure-library-third-party";
      groupId = "org.clojure";
      sha512 = "2ceef3cbba119d66a38619dc4309ee9eb5e3cccacae0a50e7f099a8df160e345e1abeaa315285fe0328c6afc842a77f6fa9d3c710745b07ce8d484caca7f47bf";
      version = "0.0-20211011-0726fdeb";

    };
    paths = [ src ];
  }

  rec {
    name = "hiccup/hiccup";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "hiccup";
      groupId = "hiccup";
      sha512 = "034f15be46c35029f41869c912f82cb2929fbbb0524ea64bd98dcdb9cf09875b28c75e926fa5fff53942b0f9e543e85a73a2d03c3f2112eecae30fcef8b148f4";
      version = "1.0.5";

    };
    paths = [ src ];
  }

  rec {
    name = "javassist/org.javassist";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "javassist";
      groupId = "org.javassist";
      sha512 = "ad65ee383ed83bedecc2073118cb3780b68b18d5fb79a1b2b665ff8529df02446ad11e68f9faaf4f2e980065f5946761a59ada379312cbb22d002625abed6a4f";
      version = "3.18.1-GA";

    };
    paths = [ src ];
  }

  rec {
    name = "xnio-nio/org.jboss.xnio";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "xnio-nio";
      groupId = "org.jboss.xnio";
      sha512 = "7f2c53222caf40793b1c956aa08ff3316a86577e6a79274050bcdf700b68546397b286e3d693c1b3036fe9175f54f76816cbf6a100ae67f9e6c20b4ca028e56d";
      version = "3.8.0.Final";

    };
    paths = [ src ];
  }

  rec {
    name = "priority-queue/shams";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "priority-queue";
      groupId = "shams";
      sha512 = "0fe37b55324e7c6bfcd338e7f72cdbaa5987c918877469685e3e23a34da0e8657c6453bf8af5378bb21ba2eff57c767857306ebcba4676525602e07c91194f8c";
      version = "0.1.2";

    };
    paths = [ src ];
  }

  rec {
    name = "reagent/reagent";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "reagent";
      groupId = "reagent";
      sha512 = "c15ceec91a05ac90d69f9499a8f1088ea794fc017ffec7b9d941f4e46a5c731704893e301f1b4d887082dc807294547e5846784a5a21ee4d768508c47b493d3a";
      version = "1.2.0";

    };
    paths = [ src ];
  }

  rec {
    name = "msgpack/org.msgpack";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "msgpack";
      groupId = "org.msgpack";
      sha512 = "a2741bed01f9c37ba3dbe6a7ab9ce936d36d4da97c35e215250ac89ac0851fc5948d83975ea6257d5dce1d43b6b5147254ecfb4b33f9bbdc489500b3ff060449";
      version = "0.6.12";

    };
    paths = [ src ];
  }

  rec {
    name = "jboss-threads/org.jboss.threads";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "jboss-threads";
      groupId = "org.jboss.threads";
      sha512 = "12d2b4c6c4f732a4b9437ae6e893087981aa2d829c9bad7089cd4cb10bccd7105e136f694e43a36e5bf234ca81294117fd9f6a07795f77c0f80d8f748c8fa529";
      version = "3.1.0.Final";

    };
    paths = [ src ];
  }

  rec {
    name = "transit-clj/com.cognitect";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "transit-clj";
      groupId = "com.cognitect";
      sha512 = "f04e0e4f76bcc684559e479cdc1cc39822eab869cc07f972040fb9778b0bcffe73a9518e9b58134f0b9c0ba4e5a115c065756b4423b4816db36eb382a9972c48";
      version = "1.0.329";

    };
    paths = [ src ];
  }

  rec {
    name = "crypto-random/crypto-random";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "crypto-random";
      groupId = "crypto-random";
      sha512 = "3520df744f250dbe061d1a5d7a05b7143f3a67a4c3f9ad87b8044ee68a36a702a0bcb3a203e35d380899dd01c28e01988b0a7af914b942ccbe0c35c9bdb22e11";
      version = "1.2.1";

    };
    paths = [ src ];
  }

  rec {
    name = "ring-codec/ring";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "ring-codec";
      groupId = "ring";
      sha512 = "38b9775a794831b8afd8d66991a75aa5910cd50952c9035866bf9cc01353810aedafbc3f35d8f9e56981ebf9e5c37c00b968759ed087d2855348b3f46d8d0487";
      version = "1.1.3";

    };
    paths = [ src ];
  }

  rec {
    name = "shadow-client/thheller";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "shadow-client";
      groupId = "thheller";
      sha512 = "b1f2ac82b31841d265af0939ecc0824e6ba8cc28d15b44c77f3abb305b5e88465b839915a222016c2ac8c7e2049d1daa2a3eebb9a58cdc9cf653bc56712b4ca7";
      version = "1.3.3";

    };
    paths = [ src ];
  }

  rec {
    name = "core.rrb-vector/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "core.rrb-vector";
      groupId = "org.clojure";
      sha512 = "8204b559d2704105741e1c9a5b8c639161a4f4af09e18b93d6af23685dd8bdebd66b64e626a4daa69069759c423640b3a3787216b78c2e62c891650e2269e8a0";
      version = "0.1.2";

    };
    paths = [ src ];
  }

  rec {
    name = "crypto-equality/crypto-equality";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "crypto-equality";
      groupId = "crypto-equality";
      sha512 = "54cf3bd28f633665962bf6b41f5ccbf2634d0db210a739e10a7b12f635e13c7ef532efe1d5d8c0120bb46478bbd08000b179f4c2dd52123242dab79fea97d6a6";
      version = "1.0.0";

    };
    paths = [ src ];
  }

  rec {
    name = "wildfly-client-config/org.wildfly.client";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "wildfly-client-config";
      groupId = "org.wildfly.client";
      sha512 = "3f442478c57f7dfac7039f6c7ae014bb2d45cdbd690ee631a3349edbca414adfe1984d065d1439f9b1546fa15fbd032c3a6bfe008f1ad50eef74201467b9f55f";
      version = "1.0.1.Final";

    };
    paths = [ src ];
  }

  rec {
    name = "jna/net.java.dev.jna";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "jna";
      groupId = "net.java.dev.jna";
      sha512 = "ee8d8aa63c67561880626a2f84412fb6996b411e065060cbe4669cc2b4e5537d09acd6d262e1924f0c066d76b18a2bd8a94e96f313b3ffd12f4735b8f6e06bb5";
      version = "5.7.0";

    };
    paths = [ src ];
  }

  rec {
    name = "tools.reader/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "tools.reader";
      groupId = "org.clojure";
      sha512 = "3481259c7a1eac719db2921e60173686726a0c2b65879d51a64d516a37f6120db8ffbb74b8bd273404285d7b25143ab5c7ced37e7c0eaf4ab1e44586ccd3c651";
      version = "1.3.6";

    };
    paths = [ src ];
  }

  rec {
    name = "nrepl/nrepl";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "nrepl";
      groupId = "nrepl";
      sha512 = "62154bd5c58b3fd315431e269d8e30a1aded912b414dac57d0fcffba740daa451238311523f8307a3bed18afbf0d3e60cac64eb3d5f54bb04f7786e8d4fa8a93";
      version = "0.9.0";

    };
    paths = [ src ];
  }

  rec {
    name = "slf4j-api/org.slf4j";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "slf4j-api";
      groupId = "org.slf4j";
      sha512 = "e5435852569dda596ba46138af8ee9c4ecba8a7a43f4f1e7897aeb4430523a0f037088a7b63877df5734578f19d331f03d7b0f32d5ae6c425df211947b3e6173";
      version = "1.7.30";

    };
    paths = [ src ];
  }

  rec {
    name = "xnio-api/org.jboss.xnio";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "xnio-api";
      groupId = "org.jboss.xnio";
      sha512 = "eab8904c5e2f6071f076e5f68fc7520fb4e9f292df2cc03be4ac7834f52994e9f539b52be7509280f4fb49a4ded185732e8a50ffd3417e39b540d508db34ac5f";
      version = "3.8.0.Final";

    };
    paths = [ src ];
  }

  rec {
    name = "closure-compiler-unshaded/com.google.javascript";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "closure-compiler-unshaded";
      groupId = "com.google.javascript";
      sha512 = "120085b36288008055e5d84a2f4fdb8c6ae4850724fdf6ae874c165e24443a93ac9fefc17fe490f634b26d048f467c5945d4f835d9bd2db50dd25473701dab91";
      version = "v20220502";

    };
    paths = [ src ];
  }

  rec {
    name = "core.memoize/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "core.memoize";
      groupId = "org.clojure";
      sha512 = "67196537084b7cc34a01454d2a3b72de3fddce081b72d7a6dc1592d269a6c2728b79630bd2d52c1bf2d2f903c12add6f23df954c02ef8237f240d7394ccc3dde";
      version = "1.0.253";

    };
    paths = [ src ];
  }

  rec {
    name = "data.priority-map/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "data.priority-map";
      groupId = "org.clojure";
      sha512 = "bb8bc5dbfd3738c36b99a51880ac3f1381d6564e67601549ef5e7ae2b900e53cdcdfb8d0fa4bf32fb8ebc4de89d954bfa3ab7e8a1122bc34ee5073c7c707ac13";
      version = "1.1.0";

    };
    paths = [ src ];
  }

  rec {
    name = "piggieback/cider";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "piggieback";
      groupId = "cider";
      sha512 = "8b7b62c6babe21764363e61259800fca25a3db175d3b7f9ff77deec457618a3303162a7645179f95e9d7c7f02c7ffcb58d1e0e237623a35b24e583fb75bb3081";
      version = "0.5.3";

    };
    paths = [ src ];
  }

  rec {
    name = "shadow-undertow/thheller";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "shadow-undertow";
      groupId = "thheller";
      sha512 = "dbf6e4e49f4fcbfc0b228ec9801cc62bbab6586fa082b10c7c8e16022a7cb93469c663ee98e4a61ce5a3369fda659e4a761442b380a9ecb00fb469b03cca16e4";
      version = "0.2.1";

    };
    paths = [ src ];
  }

  rec {
    name = "ring-core/ring";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "ring-core";
      groupId = "ring";
      sha512 = "d2b4794dc025dbf49f0ff30681b2931b313736cb19ca8716b1bb6dcc35fdce09eaded45dd938981a170816062b6a59f4d2eed1767db4447923954e7d9d06f1fb";
      version = "1.9.5";

    };
    paths = [ src ];
  }

  rec {
    name = "core.cache/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "core.cache";
      groupId = "org.clojure";
      sha512 = "0a07ceffc2fa3a536b23773eefc7ef5e1108913b93c3a5416116a6566de76dd5c218f3fb0cc19415cbaa8843838de310b76282f20bf1fc3467006c9ec373667e";
      version = "1.0.225";

    };
    paths = [ src ];
  }

  rec {
    name = "core.async/org.clojure";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "core.async";
      groupId = "org.clojure";
      sha512 = "160a77da25382d7c257eee56cfe83538620576a331e025a2d672fc26d9f04e606666032395f3c2e26247c782544816a5862348f3a921b1ffffcd309c62ac64f5";
      version = "1.5.648";

    };
    paths = [ src ];
  }

  rec {
    name = "jaxb-api/javax.xml.bind";
    src = fetchMavenArtifact {
      inherit repos;
      artifactId = "jaxb-api";
      groupId = "javax.xml.bind";
      sha512 = "0c5bfc2c9f655bf5e6d596e0c196dcb9344d6dc78bf774207c8f8b6be59f69addf2b3121e81491983eff648dfbd55002b9878132de190825dad3ef3a1265b367";
      version = "2.3.0";

    };
    paths = [ src ];
  }

  ];
  }
  