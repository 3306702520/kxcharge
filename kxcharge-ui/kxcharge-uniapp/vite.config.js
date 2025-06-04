import {
  loadEnv
} from 'vite';
import uni from '@dcloudio/vite-plugin-uni';
import path from 'path';
// import viteCompression from 'vite-plugin-compression';
import uniReadPagesV3Plugin from './sheep/router/utils/uni-read-pages-v3';
import mpliveMainfestPlugin from './sheep/libs/mplive-manifest-plugin';
import AutoImport from 'unplugin-auto-import/vite'

// https://vitejs.dev/config/
export default (command, mode) => {
  const env = loadEnv(mode, __dirname, 'SHOPRO_');
  return {
    envPrefix: "SHOPRO_",
    plugins: [
      uni(),
      // viteCompression({
      // 	verbose: false
      // }),
      uniReadPagesV3Plugin({
        pagesJsonDir: path.resolve(__dirname, './pages.json'),
        includes: ['path', 'aliasPath', 'name', 'meta'],
      }),
      mpliveMainfestPlugin(env.SHOPRO_MPLIVE_ON),
      AutoImport({
        imports: ['vue', 'uni-app'],
        dirs: ['./sheep', './sheep/hooks'],
        vueTemplate: true,
      }),
    ],
    server: {
      host: true,
      // open: true,
      port: env.SHOPRO_DEV_PORT,
      hmr: {
        overlay: true,
      },
    },
  };
};