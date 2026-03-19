import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE = 'http://localhost:4567';

// simulate existing short codes
const codes = ['0a581d', '3cf096', 'b15575', '468338'];

export const options = {
  stages: [
    { duration: '5s', target: 50 },
    { duration: '10s', target: 100 },
    { duration: '5s', target: 0 },
  ],
  thresholds: {
    http_req_duration: ['p(95)<10'],
    http_req_failed: ['rate<0.01'],
  },
};

export default function () {

  // 80% read (realistic for URL shortener)
  if (Math.random() < 0.8) {

    const code = codes[Math.floor(Math.random() * codes.length)];

    const res = http.get(`${BASE}/urls/${code}`);

    check(res, {
      'GET status 200': (r) => r.status === 200,
    });

  } else {
    // 20% create new short URL
    const payload = JSON.stringify({
      url: `https://example.com/${Math.random()}`
    });

    const res = http.post(`${BASE}/urls`, payload, {
      headers: { 'Content-Type': 'application/json' },
    });

    check(res, {
      'POST status 201': (r) => r.status === 201,
    });
  }

  // simulate user thinking time (VERY IMPORTANT)
  sleep(Math.random() * 2); // 0–2 seconds
}