class Routine {
  constructor() {}

  async getCode() {
    let provider = await this.getProvider();
    return new Promise((resolve, reject) => {
      uni.login({
        provider: provider,
        scopes: 'auth_user',
        success(res) {
          return resolve(res.code);
        },
        fail() {
          return reject(null);
        }
      })
    })
  }

  /**
   * 获取服务供应商
   */
  getProvider() {
    return new Promise((resolve, reject) => {
      uni.getProvider({
        service: 'oauth',
        success(res) {
          resolve(res.provider);
        },
        fail() {
          resolve(false);
        }
      });
    });
  }

  async getUserInfo() {
    return new Promise((resolve, reject) => {
      uni.getUserInfo({
        provider: 'weixin',
        success: res => {
          resolve({
            userInfo: res.userInfo
          });
        },
        fail: (err) => {
          reject(err);
        }
      })
    })
  }
}

export default new Routine();