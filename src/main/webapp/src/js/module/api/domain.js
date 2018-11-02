let hostname = window.location.hostname

let hostConfig = {
  'home.xiaoxuezha.com': {
    host: 'http://home.xiaoxuezha.com'
  },
  'erm.comezx.com': {
    host: 'http://erm.comezx.com'
  },
  'ermtest.comezx.com': {
    host: 'http://ermtest.comezx.com'
  },
  'localhost': {
    host: 'http://ermtest.comezx.com'
  }
}

//let domain = hostConfig[hostname]
let domain={
  host: 'http://localhost:9092'
};
export { domain }
