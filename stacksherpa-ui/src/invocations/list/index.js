import React, { useState, useEffect, useCallback } from "react";

import styled from "styled-components";

import InvocationStep from "./components/InvocationStep";

import { BASE } from "../../Constants";

const list = () => {
  return fetch(`${BASE}/invocations`, {
    mode: "cors",
  }).then((response) => response.json());
};

const post = () => {
  fetch(`${BASE}/invocations`, {
    method: "POST",
    body: JSON.stringify({ job: "job.01" }),
    mode: "cors",
    headers: {
      "content-type": "application/json",
    },
    // credentials: "omit",
    // cache: "no-cache",
  }).then((response) => {
    console.log(response.status);
  });
};

const _delete = (id) => {
  return fetch(`${BASE}/invocations/${id}`, {
    method: "DELETE",
    mode: "cors",
    // credentials: "omit",
    // cache: "no-cache",
  }).then((response) => {
    console.log(response.status);
  });
};

const InvocationList = ({ className }) => {
  const [invocations, setInvocations] = useState([]);

  const refresh = useCallback(() => {
    list().then((json) => setInvocations(json));
  }, []);

  useEffect(() => {
    const interval = setInterval(refresh, 5000);
    return () => clearInterval(interval);
  }, [refresh]);

  return (
    <div className={className}>
      <div className="header">
        <button onClick={post}>webhook</button>
      </div>
      <div>
        {invocations.map((invocation) => {
          return (
            <div className="item" key={invocation.id}>
              <div>
                {invocation.id}
                {invocation.steps.map((step) => {
                  return <InvocationStep invocation={invocation} step={step} key={step.id} />;
                })}
              </div>
              <div>
                <button onClick={() => _delete(invocation.id).then(refresh)}>delete</button>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default styled(InvocationList)`
  padding: 1rem;
  .header {
    margin-bottom: 2rem;
  }
  .item {
    display: flex;
    margin: 0 -1rem 1rem;
    > div {
      flex: 0 1 auto;
      margin: 0 1rem;
      &:first-child {
        flex: 1 1 auto;
      }
    }
  }
`;
