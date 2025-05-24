import { App } from "vue";

export function noSpace(app: App<Element>) {
  app.directive('no-space',(el, binding) => {
    el.addEventListener('input', function(e) {
      e.target.value = e.target.value.replace(/\s/g, '');
      // console.log(`output->e`,e)
    })
  })
}