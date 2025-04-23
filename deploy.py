#!/usr/bin/env python3

import os
import argparse
import subprocess
import tempfile
from mako.template import Template
from dotenv import dotenv_values


TEMPLATE_FILE = 'docker-compose.yml.in'


def port(base, i):
    return base + i


def render_compose_template(replicas):
    with open(TEMPLATE_FILE, 'r') as f:
        template = Template(f.read())

    env_vars = dotenv_values('.env')

    context = dict(env_vars)
    context['replicas'] = replicas
    context['port'] = port
    rendered = template.render(**context)

    tmp_file = tempfile.NamedTemporaryFile(mode='w+', suffix='.yml', delete=False)

    tmp_file.write(rendered)
    tmp_file.flush()

    return tmp_file


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--replicas', type=int, default=2, help='Number of replicas')
    args, docker_args = parser.parse_known_args()

    tmp_file = render_compose_template(args.replicas)
    cwd = os.getcwd()

    cmd = ['docker', 'compose', '-f', tmp_file.name, '--project-directory', cwd] + docker_args

    try:
        subprocess.run(cmd)
    finally:
        os.remove(tmp_file.name)


if __name__ == '__main__':
    main()
