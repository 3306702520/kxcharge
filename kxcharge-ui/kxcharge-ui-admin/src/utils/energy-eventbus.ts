import mitt from 'mitt';

const gwEventBus = mitt();
//Typescript 注册
declare module 'vue' {
    export interface ComponentCustomProperties {
        $Bus: typeof gwEventBus
    }
}
export default gwEventBus;