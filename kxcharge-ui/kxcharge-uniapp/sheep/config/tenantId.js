// config.header['Tenant-Id'] = tenantId;
import {
  getTenantByWebsite
} from '@/sheep/api/charge/system'

let baseUrl;
if (process.env.NODE_ENV === 'development') {
  baseUrl = import.meta.env.SHOPRO_DEV_BASE_URL;
} else {
  baseUrl = import.meta.env.SHOPRO_BASE_URL;
}

class TenantId {
  #id
  #name
  async getTenantByWebsite() {
    const {
      data
    } = await getTenantByWebsite({
      website: baseUrl
    })
    this.#id = data.id
    this.#name = data.name
  }
  getId() {
    return this.#id
  }
  getName() {
    return this.#name
  }
}

export default new TenantId()