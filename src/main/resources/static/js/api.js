/*
 * Shared front-end helpers for the temporary Thymeleaf UI.
 *
 * This file lives in src/main/resources/static/js/ so it is served as-is -
 * Thymeleaf does NOT process files under static/, only templates/. That is why
 * the URLs here are plain root-relative strings rather than @{...} expressions.
 *
 * When React arrives, this file becomes your api client module almost verbatim.
 */

const TOKEN_KEY = 'winzz.jwt';

const ROUTES = {
    login:       '/ui/login',
    dashboard:   '/ui/dashboard',
    tournaments: '/ui/tournaments',
    scoreboard:  '/ui/scoreboard',
    player:      '/ui/player'
};

const Auth = {

    getToken() {
        return localStorage.getItem(TOKEN_KEY);
    },

    setToken(token) {
        localStorage.setItem(TOKEN_KEY, token);
    },

    clear() {
        localStorage.removeItem(TOKEN_KEY);
    },

    /** Bounce to the login page if there is no token. Returns false if it did. */
    require() {
        if (!this.getToken()) {
            window.location.replace(ROUTES.login);
            return false;
        }
        return true;
    },

    logout() {
        this.clear();
        window.location.href = ROUTES.login;
    },

    /** Best-effort username straight out of the JWT payload (never trusted for security). */
    username() {
        try {
            const payload = JSON.parse(atob(this.getToken().split('.')[1]));
            return payload.sub || null;
        } catch (e) {
            return null;
        }
    }
};

/**
 * fetch() with the Authorization header attached.
 *
 * A 401/403 means the token is missing, invalid or expired - there is nothing
 * the page can do about it, so we clear it and send the user back to login.
 * Centralising that here means no page has to repeat the check.
 */
async function authFetch(url, options) {
    options = options || {};

    const headers = Object.assign({}, options.headers, {
        'Authorization': 'Bearer ' + Auth.getToken()
    });

    const response = await fetch(url, Object.assign({}, options, { headers: headers }));

    if (response.status === 401 || response.status === 403) {
        Auth.clear();
        window.location.href = ROUTES.login;
        throw new Error('Unauthorized');
    }

    return response;
}

/** Read a query-string parameter, e.g. ?tournamentId=3 */
function queryParam(name) {
    return new URLSearchParams(window.location.search).get(name);
}

/** Escape text before putting it into innerHTML. */
function esc(value) {
    if (value === null || value === undefined) return '';
    return String(value)
        .replace(/&/g, '&amp;').replace(/</g, '&lt;')
        .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

/** Wire up any element with data-logout. */
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('[data-logout]').forEach(function (el) {
        el.addEventListener('click', function () { Auth.logout(); });
    });
});
