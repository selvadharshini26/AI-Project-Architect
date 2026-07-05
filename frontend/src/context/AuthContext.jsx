import { createContext, useContext, useMemo, useState, useCallback } from 'react';
import { authApi } from '../lib/api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const raw = localStorage.getItem('apa_user');
    return raw ? JSON.parse(raw) : null;
  });

  const persist = useCallback((authResponse) => {
    const nextUser = {
      userId: authResponse.userId,
      username: authResponse.username,
      email: authResponse.email,
      roles: authResponse.roles,
    };
    localStorage.setItem('apa_token', authResponse.token);
    localStorage.setItem('apa_user', JSON.stringify(nextUser));
    setUser(nextUser);
  }, []);

  const login = useCallback(
    async (usernameOrEmail, password) => {
      const res = await authApi.login({ usernameOrEmail, password });
      persist(res);
      return res;
    },
    [persist]
  );

  const register = useCallback(
    async (username, email, password) => {
      const res = await authApi.register({ username, email, password });
      persist(res);
      return res;
    },
    [persist]
  );

  const logout = useCallback(() => {
    localStorage.removeItem('apa_token');
    localStorage.removeItem('apa_user');
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({ user, isAuthenticated: !!user, login, register, logout }),
    [user, login, register, logout]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within an AuthProvider');
  return ctx;
}
